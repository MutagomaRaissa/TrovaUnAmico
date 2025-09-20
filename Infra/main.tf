terraform {
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "5.10.0"
    }
  }
}

provider "google" {
  project = var.project_id
  region  = var.region
  zone    = var.zone
}

# Persistent disk for PostgreSQL
resource "google_compute_disk" "postgres_disk" {
  name  = "postgres-disk"
  type  = "pd-standard"
  zone  = var.zone
  size  = var.db_disk_size
}

# VM Instance
resource "google_compute_instance" "trovaunamico_vm" {
  name         = var.vm_name
  machine_type = "e2-micro" # Free tier
  zone         = var.zone

  boot_disk {
    initialize_params {
      image  = "ubuntu-os-cloud/ubuntu-2204-lts"
      size   = 20
      type   = "pd-standard"
    }
  }

  # Attach persistent disk for Postgres
  attached_disk {
    source = google_compute_disk.postgres_disk.id
    mode   = "READ_WRITE"
    device_name = "postgres-disk"
  }

  network_interface {
    network = "default"
    access_config {}
  }

  metadata_startup_script = <<-EOT
    #!/bin/bash
    apt-get update
    apt-get install -y docker.io docker-compose git
    systemctl enable docker
    systemctl start docker

    # Create docker-compose.yml
    cat > /home/ubuntu/docker-compose.yml <<EOF
    version: '3.9'
    services:
      amico_db:
        image: postgres:17.5
        environment:
          POSTGRES_USER: postgres
          POSTGRES_PASSWORD: ${var.db_password}
          POSTGRES_DB: postgres
        ports:
          - "5432:5432"
        volumes:
          - postgres_data:/var/lib/postgresql/data
        healthcheck:
          test: ["CMD-SHELL", "pg_isready -U postgres"]
          interval: 5s
          timeout: 5s
          retries: 5

      trovaunamico_app:
        image: ${var.docker_image}
        environment:
          SPRING_DATASOURCE_URL: jdbc:postgresql://amico_db:5432/postgres
          SPRING_DATASOURCE_USERNAME: postgres
          SPRING_DATASOURCE_PASSWORD: var.db_password
          GOOGLE_CLIENT_ID:var.google_client_id
          GOOGLE_CLIENT_SECRET:var.google_client_secret
          SPRING_SENDGRID_API_KEY:var.sendgrid_api_key
          SPRING_SENDGRID_SENDER_EMAIL: "noreply.trovaunamico@gmail.com"
        ports:
          - "8080:8080"
        depends_on:
          - amico_db

    volumes:
      postgres_data:
        driver: local
        driver_opts:
          type: none
          device: /mnt/disks/postgres-disk
          o: bind
    EOF

    # Mount disk
    mkdir -p /mnt/disks/postgres-disk
    mount -o discard,defaults /dev/disk/by-id/google-postgres-disk /mnt/disks/postgres-disk

    # Start docker-compose
    docker-compose -f /home/ubuntu/docker-compose.yml up -d
  EOT

  tags = ["http-server"]
}

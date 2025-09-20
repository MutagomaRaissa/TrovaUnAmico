variable "project_id" {
  description = "GCP Project ID"
}

variable "region" {
  default     = "europe-west8" # Milan
  description = "GCP region"
}

variable "zone" {
  default     = "europe-west8-b"
  description = "GCP zone"
}

variable "vm_name" {
  default     = "trovaunamico-vm"
  description = "Name of the VM"
}

variable "docker_image" {
  default     = "rmutagoma/trovaunamico_app:latest"
  description = "Docker Hub image for the Spring Boot app"
}

variable "db_password" {
  default     = "password"
  description = "PostgreSQL password"
}

variable "db_disk_size" {
  default     = 20
  description = "Size of the persistent disk for PostgreSQL in GB"
}

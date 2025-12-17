variable "region" {
  description = "The AWS region to deploy resources in"
  default     = "us-east-1"
}

variable "force_rollout" {
  description = "A dummy variable to force redeployment of resources"
  type        = string
  default     = ""
}

variable "app_image" {
  description = "The Docker image for the application"
}

variable "db_user" {
  description = "The username for the RDS instance"
  sensitive   = true
}

variable "db_password" {
  description = "The password for the RDS instance"
  sensitive   = true
}

variable "jwt_expiration_time" {
  description = "The expiration time for JWT tokens in seconds"
  default     = "3600"
}

variable "tags" {
  description = "A map of tags to assign to resources"
  default = {
    Environment = "PRD"
    Project     = "tc-app"
  }
}

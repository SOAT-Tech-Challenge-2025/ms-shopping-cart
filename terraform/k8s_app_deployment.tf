resource "kubernetes_deployment" "app" {
  metadata {
    name      = "ms-shopping-cart-deployment"
    namespace = kubernetes_namespace.tech_challenge.metadata[0].name
  }

  spec {
    replicas = 1
    strategy {
      type = "Recreate"
    }
    selector {
      match_labels = {
        app = "ms-shopping-cart-app"
      }
    }

    template {
      metadata {
        labels = {
          app = "ms-shopping-cart-app"
        }

        annotations = {
          "restarted-at" = var.force_rollout
        }
      }

      spec {
        container {
          name              = "ms-shopping-cart-app"
          image             = var.app_image
          image_pull_policy = "Always"

          port {
            container_port = 8080
          }

          resources {
            limits = {
              cpu    = "1"
              memory = "1Gi"
            }
            requests = {
              cpu    = "500m"
              memory = "512Mi"
            }
          }

          env {
            name = "SPRING_DATASOURCE_URL"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.app_secret.metadata[0].name
                key  = "SPRING_DATASOURCE_URL"
              }
            }
          }

          env {
            name = "SPRING_DATASOURCE_USERNAME"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.app_secret.metadata[0].name
                key  = "SPRING_DATASOURCE_USERNAME"
              }
            }
          }

          env {
            name = "SPRING_DATASOURCE_PASSWORD"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.app_secret.metadata[0].name
                key  = "SPRING_DATASOURCE_PASSWORD"
              }
            }
          }
          env {
            name = "SNS_TOPIC_ARN"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.app_secret.metadata[0].name
                key  = "SNS_TOPIC_ARN"
              }
            }
          }
        }
      }
    }
  }
}

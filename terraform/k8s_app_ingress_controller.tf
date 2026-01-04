resource "kubernetes_ingress_v1" "app" {
  metadata {
    name      = "ms-shopping-cart-route"
    namespace = kubernetes_namespace.tech_challenge.metadata[0].name
  }

  spec {
    ingress_class_name = "nginx"

    rule {
      http {
        path {
          path      = "/soat-fast-food/v1"
          path_type = "Prefix"

          backend {
            service {
              name = kubernetes_service.app.metadata[0].name
              port {
                number = 80
              }
            }
          }
        }
      }
    }
  }
}

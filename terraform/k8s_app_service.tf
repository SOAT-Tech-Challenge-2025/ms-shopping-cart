resource "kubernetes_service" "app" {
  metadata {
    name      = "ms-shopping-cart-service"
    namespace = kubernetes_namespace.tech_challenge.metadata[0].name
  }

  spec {
    type = "ClusterIP"

    selector = {
      app = "tech-challenge-app"
    }

    port {
      port        = 80
      target_port = 8080
    }
  }
}

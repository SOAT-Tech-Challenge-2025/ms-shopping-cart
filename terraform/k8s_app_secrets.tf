resource "kubernetes_secret" "app_secret" {
  metadata {
    name      = "ms-shopping-cart-secret"
    namespace = kubernetes_namespace.tech_challenge.metadata[0].name
  }

  type = "Opaque"

  data = {
    SPRING_DATASOURCE_URL      = local.jdbc_url
    SPRING_DATASOURCE_USERNAME = var.db_user
    SPRING_DATASOURCE_PASSWORD = var.db_password
  }
}

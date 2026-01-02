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
    SNS_TOPIC_ARN              = var.sns_topic_arn
    AWS_ACCESS_KEY_ID          = var.aws_access_key
    AWS_SECRET_ACCESS_KEY      = var.aws_access_secret
    AWS_REGION                 = var.aws_region
  }
}

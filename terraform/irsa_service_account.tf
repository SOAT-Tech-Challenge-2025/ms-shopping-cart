resource "kubernetes_service_account" "ms_shopping_cart_sa" {
  metadata {
    name      = "ms-shopping-cart-sa"
    namespace = kubernetes_namespace.tech_challenge.metadata[0].name

    annotations = {
      "eks.amazonaws.com/role-arn" = aws_iam_role.ms_shopping_cart_irsa.arn
    }
  }
}

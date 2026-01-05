locals {
  oidc_provider_arn = data.terraform_remote_state.infra.outputs.oidc_provider_arn
  oidc_provider_url = data.terraform_remote_state.infra.outputs.oidc_provider_url
  cluster_name      = data.terraform_remote_state.infra.outputs.cluster_name
}



resource "aws_iam_policy" "ms_shopping_cart_sns_publish" {
  name = "ms-shopping-cart-sns-publish"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect   = "Allow"
        Action   = "sns:Publish"
        Resource = "arn:aws:sns:us-east-1:089175280184:order-created.fifo"
      }
    ]
  })
}
resource "aws_iam_role" "ms_shopping_cart_irsa" {
  name = "ms-shopping-cart-sns-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Principal = {
          Federated = local.oidc_provider_arn
        }
        Action = "sts:AssumeRoleWithWebIdentity"
        Condition = {
          StringEquals = {
            "${replace(local.oidc_provider_url, "https://", "")}:sub" = "system:serviceaccount:tech-challenge:ms-shopping-cart-sa"
          }
        }

      }
    ]
  })
}
resource "aws_iam_role_policy_attachment" "ms_shopping_cart_attach" {
  role       = aws_iam_role.ms_shopping_cart_irsa.name
  policy_arn = aws_iam_policy.ms_shopping_cart_sns_publish.arn
}
resource "kubernetes_service_account" "ms_shopping_cart_sa" {
  metadata {
    name      = "ms-shopping-cart-sa"
    namespace = "tech-challenge"

    annotations = {
      "eks.amazonaws.com/role-arn" = aws_iam_role.ms_shopping_cart_irsa.arn
    }
  }
}

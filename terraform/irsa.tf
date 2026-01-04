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
            format(
              "%s:sub",
              replace(local.oidc_provider_url, "https://", "")
            ) = "system:serviceaccount:tech-challenge:ms-shopping-cart-sa"
          }
        }
      }
    ]
  })
}

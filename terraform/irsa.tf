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
            ("${replace(local.oidc_provider_url, "https://", "")}:sub") =
            "system:serviceaccount:tech-challenge:ms-shopping-cart-sa"
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

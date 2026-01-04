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

resource "aws_iam_policy" "ms_shopping_cart_sns_publish" {
  name        = "ms-shopping-cart-sns-publish"
  description = "Permite o ms-shopping-cart publicar mensagens no SNS"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "sns:Publish"
        ]
        Resource = "arn:aws:sns:us-east-1:089175280184:order-created.fifo"
      }
    ]
  })
}
resource "aws_iam_role_policy_attachment" "ms_shopping_cart_sns_attach" {
  role       = aws_iam_role.ms_shopping_cart_irsa.name
  policy_arn = aws_iam_policy.ms_shopping_cart_sns_publish.arn
}


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

data "aws_iam_policy" "ms_shopping_cart_sns_publish" {
  name = "ms-shopping-cart-sns-publish"
}


resource "aws_iam_role_policy_attachment" "ms_shopping_cart_sns_attach" {
  role       = aws_iam_role.ms_shopping_cart_irsa.name
  policy_arn = data.aws_iam_policy.ms_shopping_cart_sns_publish.arn
}


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

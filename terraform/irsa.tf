data "aws_iam_policy_document" "ms_shopping_cart_assume_role" {
  statement {
    effect  = "Allow"
    actions = ["sts:AssumeRoleWithWebIdentity"]

    principals {
      type        = "Federated"
      identifiers = [local.oidc_provider_arn]
    }

    condition {
      test     = "StringEquals"
      variable = "${replace(local.oidc_provider_url, "https://", "")}:sub"
      values   = ["system:serviceaccount:tech-challenge:ms-shopping-cart-sa"]
    }
  }
}

resource "aws_iam_role" "ms_shopping_cart_irsa" {
  name               = "ms-shopping-cart-sns-role"
  assume_role_policy = data.aws_iam_policy_document.ms_shopping_cart_assume_role.json
}

resource "aws_iam_role_policy_attachment" "ms_shopping_cart_attach" {
  role       = aws_iam_role.ms_shopping_cart_irsa.name
  policy_arn = aws_iam_policy.ms_shopping_cart_sns_publish.arn
}

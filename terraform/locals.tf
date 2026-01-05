locals {
  api_gateway_url   = data.terraform_remote_state.infra.outputs.api_gateway_url
  jdbc_url          = "jdbc:postgresql://${data.aws_db_instance.db_instance.address}:${data.aws_db_instance.db_instance.port}/shopping_cart"
  oidc_provider_arn = data.terraform_remote_state.infra.outputs.oidc_provider_arn
  oidc_provider_url = data.terraform_remote_state.infra.outputs.oidc_provider_url
  cluster_name      = data.terraform_remote_state.infra.outputs.cluster_name
}

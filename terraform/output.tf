# =====================================
# Espera o ingress ficar pronto
# =====================================
resource "null_resource" "wait_ingress" {
  depends_on = [kubernetes_ingress_v1.app]

  provisioner "local-exec" {
    command = <<EOT
      echo "Aguardando Ingress do ms-shopping-cart ficar pronto..."
      for i in {1..20}; do
        HOST=$(kubectl get ingress ms-shopping-cart-route -n ${kubernetes_namespace.tech_challenge.metadata[0].name} -o jsonpath='{.status.loadBalancer.ingress[0].hostname}')
        if [ -n "$HOST" ]; then
          echo "Ingress pronto: $HOST" > ingress_host.txt
          exit 0
        fi
        echo "Ainda não pronto, tentando novamente em 10s..."
        sleep 10
      done
      echo "Ingress ainda não está pronto após 200s!"
      exit 1
    EOT
  }
}

# =====================================
# Output do hostname capturado pelo script
# =====================================
output "ingress_host_final" {
  description = "Host final do Ingress do ms-shopping-cart"
  value       = file("${path.module}/ingress_host.txt")
  depends_on  = [null_resource.wait_ingress]
}

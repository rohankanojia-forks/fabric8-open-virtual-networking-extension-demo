package io.fabric8.ovn.client.demos;

import io.fabric8.kubernetes.api.model.ovn.v1.EgressIP;
import io.fabric8.kubernetes.api.model.ovn.v1.EgressIPBuilder;
import io.fabric8.kubernetes.api.model.ovn.v1.EgressIPList;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.ovn.client.OpenVirtualNetworkingClient;

import java.util.Collections;

public class ListEgressIPDemo {
  public static void main(String[] args) {
    try (OpenVirtualNetworkingClient ovnClient = new KubernetesClientBuilder().build().adapt(OpenVirtualNetworkingClient.class)) {
      EgressIP egressIP = new EgressIPBuilder()
          .withNewMetadata()
          .withName("egressip-prod")
          .endMetadata()
          .withNewSpec()
          .addToEgressIPs("172.18.0.33", "172.18.0.44")
          .withNewNamespaceSelector()
          .addNewMatchExpression()
          .withKey("environment")
          .withOperator("NotIn")
          .withValues("development")
          .endMatchExpression()
          .endNamespaceSelector()
          .withNewPodSelector()
          .withMatchLabels(Collections.singletonMap("app", "web"))
          .endPodSelector()
          .endSpec()
          .build();
      ovnClient.v1().egressIps().resource(egressIP).serverSideApply();

      EgressIPList egressIPList = ovnClient.v1().egressIps().list();
      for (EgressIP egressIPItem : egressIPList.getItems()) {
        System.out.println(egressIPItem.getMetadata().getName());
      }
    }
  }
}

package io.fabric8.ovn.client.demos;

import io.fabric8.kubernetes.api.model.ovn.v1.EgressService;
import io.fabric8.kubernetes.api.model.ovn.v1.EgressServiceBuilder;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.ovn.client.OpenVirtualNetworkingClient;

import java.util.Collections;

public class CreateEgressServiceDemo {
  public static void main(String[] args) {
    try (OpenVirtualNetworkingClient ovnClient = new KubernetesClientBuilder().build().adapt(OpenVirtualNetworkingClient.class)) {
      EgressService egressService = new EgressServiceBuilder()
          .withNewMetadata()
          .withName("test-svc")
          .endMetadata()
          .withNewSpec()
          .withSourceIPBy("LoadBalancerIP")
          .withNewNodeSelector()
          .withMatchLabels(Collections.singletonMap("node-role.kubernetes.io/worker", ""))
          .endNodeSelector()
          .endSpec()
          .build();
      egressService = ovnClient.v1().egressServices().resource(egressService).serverSideApply();

      egressService = ovnClient.v1().egressServices().resource(egressService).get();
      System.out.println(egressService.getMetadata().getName() + " Created");
    }
  }
}

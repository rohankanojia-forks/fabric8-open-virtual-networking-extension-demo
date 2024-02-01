package io.fabric8.ovn.client.demos;

import io.fabric8.kubernetes.api.model.ovn.v1.EgressQoS;
import io.fabric8.kubernetes.api.model.ovn.v1.EgressQoSBuilder;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.ovn.client.OpenVirtualNetworkingClient;

import java.util.Collections;

public class EgressQoSCreateDemo {
  public static void main(String[] args) {
    try (OpenVirtualNetworkingClient ovnClient = new KubernetesClientBuilder().build().adapt(OpenVirtualNetworkingClient.class)) {
      EgressQoS egressQoS = new EgressQoSBuilder()
          .withNewMetadata()
          .withName("default")
          .endMetadata()
          .withNewSpec()
          .addNewEgress()
          .withDscp(48)
          .withNewPodSelector()
          .withMatchLabels(Collections.singletonMap("app", "updated-example"))
          .endPodSelector()
          .endEgress()
          .addNewEgress()
          .withDscp(28)
          .endEgress()
          .endSpec()
          .build();
      egressQoS = ovnClient.v1().egressQoses().resource(egressQoS).serverSideApply();

      egressQoS = ovnClient.v1().egressQoses().resource(egressQoS).get();
      System.out.println(egressQoS.getMetadata().getName() + " Created");
    }
  }
}

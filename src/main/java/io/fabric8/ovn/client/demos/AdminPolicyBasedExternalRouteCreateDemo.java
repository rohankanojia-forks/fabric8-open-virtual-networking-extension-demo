package io.fabric8.ovn.client.demos;

import io.fabric8.kubernetes.api.model.ovn.v1.AdminPolicyBasedExternalRoute;
import io.fabric8.kubernetes.api.model.ovn.v1.AdminPolicyBasedExternalRouteBuilder;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.ovn.client.OpenVirtualNetworkingClient;

import java.util.Collections;

public class AdminPolicyBasedExternalRouteCreateDemo {
  public static void main(String[] args) {
    try (OpenVirtualNetworkingClient ovnClient = new KubernetesClientBuilder().build().adapt(OpenVirtualNetworkingClient.class)) {
      AdminPolicyBasedExternalRoute adminPolicyBasedExternalRoute = new AdminPolicyBasedExternalRouteBuilder()
          .withNewMetadata()
          .withName("test-adminpolicybasedexternalroute")
          .endMetadata()
          .withNewSpec()
          .withNewFrom()
          .withNewNamespaceSelector()
          .withMatchLabels(Collections.singletonMap("multiple_gws", "true"))
          .endNamespaceSelector()
          .endFrom()
          .withNewNextHops()
          .addNewStatic()
          .withIp("172.18.0.2")
          .withBfdEnabled(true)
          .endStatic()
          .addNewDynamic()
          .withBfdEnabled(true)
          .withNewNamespaceSelector()
          .withMatchLabels(Collections.singletonMap("gateway", "true"))
          .endNamespaceSelector()
          .withNewPodSelector()
          .withMatchLabels(Collections.singletonMap("external-gateway", "true"))
          .endPodSelector()
          .endDynamic()
          .endNextHops()
          .endSpec()
          .build();
      adminPolicyBasedExternalRoute = ovnClient.v1().adminPolicyBasedExternalRoutes().resource(adminPolicyBasedExternalRoute).serverSideApply();

      adminPolicyBasedExternalRoute = ovnClient.v1().adminPolicyBasedExternalRoutes().resource(adminPolicyBasedExternalRoute).get();
      System.out.println(adminPolicyBasedExternalRoute.getMetadata().getName() + " Created");
    }
  }
}

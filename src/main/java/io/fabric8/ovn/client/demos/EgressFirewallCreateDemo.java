package io.fabric8.ovn.client.demos;

import io.fabric8.kubernetes.api.model.ovn.v1.EgressFirewall;
import io.fabric8.kubernetes.api.model.ovn.v1.EgressFirewallBuilder;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.ovn.client.OpenVirtualNetworkingClient;

public class EgressFirewallCreateDemo {
  public static void main(String[] args) {
    try (OpenVirtualNetworkingClient ovnClient = new KubernetesClientBuilder().build().adapt(OpenVirtualNetworkingClient.class)) {
      EgressFirewall egressFirewall = new EgressFirewallBuilder()
          .withNewMetadata()
          .withName("default")
          .endMetadata()
          .withNewSpec()
          .addNewEgress()
          .withType("Allow")
          .withNewTo()
          .withDnsName("www.openvswitch.org")
          .endTo()
          .endEgress()
          .addNewEgress()
          .withType("Allow")
          .withNewTo()
          .withCidrSelector("1.2.3.0/24")
          .endTo()
          .addNewPort()
          .withProtocol("UDP")
          .withPort(55)
          .endPort()
          .endEgress()
          .addNewEgress()
          .withType("Deny")
          .withNewTo()
          .withCidrSelector("0.0.0.0/0")
          .endTo()
          .endEgress()
          .endSpec()
          .build();
      egressFirewall = ovnClient.v1().egressFirewalls().resource(egressFirewall).serverSideApply();

      egressFirewall = ovnClient.v1().egressFirewalls().resource(egressFirewall).get();
      System.out.println(egressFirewall.getMetadata().getName() + " Created");
    }
  }
}

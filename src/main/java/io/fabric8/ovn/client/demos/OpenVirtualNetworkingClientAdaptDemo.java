package io.fabric8.ovn.client.demos;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.ovn.client.OpenVirtualNetworkingClient;

public class OpenVirtualNetworkingClientAdaptDemo {
  public static void main(String[] args) {
    try (KubernetesClient client = new KubernetesClientBuilder().build()) {
      OpenVirtualNetworkingClient ovnClient = client.adapt(OpenVirtualNetworkingClient.class);
      if (ovnClient == null) {
        System.out.println("Current cluster is not compatible to use Open Virtual Networking CRDs. Please install k8s.ovn.org CRDs and try again");
      }
      System.out.println("Success");
    }
  }
}

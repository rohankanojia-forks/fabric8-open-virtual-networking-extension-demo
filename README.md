# Fabric8 Open Virtual Networking Extension Demo

This demo project showcases Fabric8 Open Virtual Networking extension, which exposes CustomResources of [ovn-kubernetes](https://github.com/ovn-org/ovn-kubernetes).

In order to use it, you need to add this dependency to your project:
```xml
    <dependency>
      <groupId>io.fabric8</groupId>
      <artifactId>open-virtual-networking-client</artifactId>
      <version>${fabric8.version}</version>
    </dependency>
```

Here is an example of sample usage:
```java
    try (OpenVirtualNetworkingClient ovnClient = new KubernetesClientBuilder().build().adapt(OpenVirtualNetworkingClient.class)) {
      EgressFirewall egressFirewall = new EgressFirewallBuilder()
          .withNewMetadata()
          .withName("default")
          // ...
          .build();
      egressFirewall = ovnClient.v1().egressFirewalls().resource(egressFirewall).serverSideApply();

      egressFirewall = ovnClient.v1().egressFirewalls().resource(egressFirewall).get();
      System.out.println(egressFirewall.getMetadata().getName() + " Created");
    }
```

You can see other examples in `src/main/resources`

package io.fabric8.ovn.client.it;

import io.fabric8.junit.jupiter.api.RequireK8sSupport;
import io.fabric8.kubernetes.api.model.ovn.v1.EgressIP;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.ovn.client.OpenVirtualNetworkingClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@RequireK8sSupport(EgressIP.class)
class OpenShiftClientOVNDSLIT {
  private static OpenVirtualNetworkingClient ovnClient = new KubernetesClientBuilder().build().adapt(OpenVirtualNetworkingClient.class);

  @BeforeEach
  void setup() {
    ovnClient = new KubernetesClientBuilder().build().adapt(OpenVirtualNetworkingClient.class);
  }

  @AfterEach
  void tearDown() {
    ovnClient.close();
  }

  static Stream<Arguments> dslEntrypoints() {
    return Stream.of(
        arguments(ovnClient.v1().egressIps()),
        arguments(ovnClient.v1().egressFirewalls()),
        arguments(ovnClient.v1().egressQoses()),
        arguments(ovnClient.v1().egressServices()),
        arguments(ovnClient.v1().adminPolicyBasedExternalRoutes())
    );
  }

  @ParameterizedTest
  @MethodSource("dslEntrypoints")
  void testList(MixedOperation mixedOperation) {
    mixedOperation.list();
  }
}

package io.fabric8.ovn.client.it;

import io.fabric8.junit.jupiter.api.RequireK8sSupport;
import io.fabric8.kubernetes.api.model.ovn.v1.AdminPolicyBasedExternalRoute;
import io.fabric8.kubernetes.api.model.ovn.v1.EgressFirewall;
import io.fabric8.kubernetes.api.model.ovn.v1.EgressIP;
import io.fabric8.kubernetes.api.model.ovn.v1.EgressQoS;
import io.fabric8.kubernetes.api.model.ovn.v1.EgressService;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@RequireK8sSupport(EgressIP.class)
class OpenShiftOVNModelIT {
  private KubernetesClient kubernetesClient;

  @BeforeEach
  void setUp() {
    kubernetesClient = new KubernetesClientBuilder().build();
  }

  @AfterEach
  void tearDown() {
    kubernetesClient.close();
  }

  @ParameterizedTest
  @ValueSource(classes = {
      AdminPolicyBasedExternalRoute.class,
      EgressFirewall.class,
      EgressIP.class,
      EgressQoS.class,
      EgressService.class
  })
  void testList(Class ovnClassToTest) {
    kubernetesClient.resources(ovnClassToTest).list();
  }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rackspace.papi.service.datastore.impl.distributed.jetty;

import com.rackspace.papi.domain.ReposeInstanceInfo;
import com.rackspace.papi.service.datastore.DatastoreService;
import com.rackspace.papi.service.datastore.impl.PowerApiDatastoreService;
import org.eclipse.jetty.server.Server;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class DistributedDatastoreJettyServerBuilderTest {

   public static class WhenCreatingDistributedDatastoreBuilder {

      private DatastoreService datastoreService;
      private ReposeInstanceInfo instanceInfo;
      
      @Before
      public void setUp() {
         
         datastoreService = new PowerApiDatastoreService();
         instanceInfo = new ReposeInstanceInfo("repose", "node1");
      }
      
      @Test
      public void shouldCreateNewServer(){
         
         DistributedDatastoreJettyServerBuilder builder = new DistributedDatastoreJettyServerBuilder(8888, instanceInfo, "/etc/repose");
         final Server server = builder.newServer(datastoreService, instanceInfo);
         
         assertTrue(server instanceof Server);
         
      }
   }
}
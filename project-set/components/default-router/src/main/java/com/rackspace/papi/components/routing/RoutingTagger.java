package com.rackspace.papi.components.routing;

import com.rackspace.papi.commons.util.servlet.http.ReadableHttpServletResponse;
import com.rackspace.papi.filter.SystemModelInterrogator;
import com.rackspace.papi.filter.logic.FilterAction;
import com.rackspace.papi.filter.logic.FilterDirector;
import com.rackspace.papi.filter.logic.common.AbstractFilterLogicHandler;
import com.rackspace.papi.filter.logic.impl.FilterDirectorImpl;
import com.rackspace.papi.model.Destination;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoutingTagger extends AbstractFilterLogicHandler {

   private static final Logger LOG = LoggerFactory.getLogger(RoutingTagger.class);
   private final SystemModelInterrogator modelInterrogator;

   public RoutingTagger(SystemModelInterrogator modelInterrogator) {
      this.modelInterrogator = modelInterrogator;
   }

   @Override
   public FilterDirector handleRequest(HttpServletRequest request, ReadableHttpServletResponse response) {
      final FilterDirector myDirector = new FilterDirectorImpl();
      myDirector.setFilterAction(FilterAction.PASS);

      Destination defaultDest = modelInterrogator.getDefaultDestination();
      
      if (defaultDest != null) {
         myDirector.addDestination(defaultDest, request.getRequestURI(), -1);
      } else {
         LOG.warn("No default destination configured for service domain: " + modelInterrogator.getLocalServiceDomain().getId());
      }

      /*
      final String firstRoutingDestination = request.getHeader(PowerApiHeader.NEXT_ROUTE.toString());
      * 
      if (firstRoutingDestination == null) {
         final Destination nextRoutableHost = modelInterrogator.getDefaultDestination();

         // TODO Model: add destination to possible next routes
         try {
            myDirector.requestHeaderManager().putHeader(PowerApiHeader.NEXT_ROUTE.toString(), HostUtilities.asUrl(nextRoutableHost, request.getRequestURI()));
         } catch (MalformedURLException murle) {
            // Malformed URL Expcetions are unexpected and should return as a 502
            LOG.error(murle.getMessage(), murle);

            myDirector.setFilterAction(FilterAction.RETURN);
            myDirector.setResponseStatus(HttpStatusCode.BAD_GATEWAY);
         }
      }
      * 
      */

      return myDirector;
   }

   @Override
   public FilterDirector handleResponse(HttpServletRequest request, ReadableHttpServletResponse response) {
      return super.handleResponse(request, response);
   }
}

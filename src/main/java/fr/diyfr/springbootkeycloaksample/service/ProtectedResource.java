package fr.diyfr.springbootkeycloaksample.service;

import fr.diyfr.springbootkeycloaksample.service.domain.ProtectedInfo;
import org.apache.commons.logging.LogFactory;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProtectedResource {

    private static org.apache.commons.logging.Log logger = LogFactory.getLog(ProtectedResource.class);

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/info",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<List<ProtectedInfo>> getDevices() {
        List<ProtectedInfo> result = new ArrayList<ProtectedInfo>();
        ProtectedInfo i = new ProtectedInfo();
        AccessToken accessToken = ((KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName())).getToken();
        // ROLES ->  Set<String> roles = accessToken.getRealmAccess().getRoles();
        i.info = accessToken.getPreferredUsername();
        result.add(i);
        return new ResponseEntity<List<ProtectedInfo>>(result, HttpStatus.OK);
    }
}

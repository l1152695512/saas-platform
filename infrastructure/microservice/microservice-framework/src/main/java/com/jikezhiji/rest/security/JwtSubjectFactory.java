package com.jikezhiji.rest.security;

import com.jikezhiji.core.Subject;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.impl.DefaultClaims;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by E355 on 2016/9/13.
 */
public class JwtSubjectFactory implements SubjectFactory<String>{
    private JwtParser parser;
    public JwtSubjectFactory(JwtParser parser){
        this.parser = parser;
    }

    public Subject createSubject(String token) {
        Jwt<Header,JWTClaims> jwt = parser.parse(token);
        JWTClaims jwtClaims = jwt.getBody();

        Set<String> roles = jwtClaims.getUserRoleAndRemove();
        Set<Subject.Role> roleSet = roles.stream().map((roleCode)->new Subject.Role(roleCode)).collect(Collectors.toSet());

        String identifiers = jwtClaims.getUserIdAndRemove();
        Subject.Visitor visitor = new Subject.Visitor(identifiers);
        visitor.setAttributes(jwtClaims);
        return new Subject(visitor,roleSet);
    }

    private class JWTClaims extends DefaultClaims {

        String IDENTIFIERS = "userId";

        String ROLE = "userRole";


        public String getUserIdAndRemove(){
            return String.class.cast(get(IDENTIFIERS));
        }
        public Set<String> getUserRoleAndRemove(){
            return Set.class.cast(get(ROLE));
        }
    }
}

package com.banksecure.security.filter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private static final int MAX_TENTATIVES = 5;
    private static final long DUREE_BLOCAGE_MS = 15 * 60 * 1000;

    private final Map<String, Integer> tentatives = new ConcurrentHashMap<>();
    private final Map<String, Long> tempsBlocage = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String ip = request.getRemoteAddr();

        // 🔹 Appliquer seulement au login
        if (request.getServletPath().equals("/api/auth/login")) {

            // 🔴 Vérifier si bloqué
            if (tempsBlocage.containsKey(ip)) {
                long finBlocage = tempsBlocage.get(ip);

                if (System.currentTimeMillis() < finBlocage) {
                    response.setStatus(429);
                    response.getWriter().write("Trop de tentatives. Réessayez plus tard.");
                    return;
                } else {
                    // débloquer
                    tempsBlocage.remove(ip);
                    tentatives.remove(ip);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    // 🔥 À appeler quand login échoue
    public void enregistrerEchec(String ip) {
        int nbEchecs = tentatives.getOrDefault(ip, 0) + 1;
        tentatives.put(ip, nbEchecs);

        if (nbEchecs >= MAX_TENTATIVES) {
            tempsBlocage.put(ip, System.currentTimeMillis() + DUREE_BLOCAGE_MS);
        }
    }

    public void reset(String ip) {
        tentatives.remove(ip);
        tempsBlocage.remove(ip);
    }
}

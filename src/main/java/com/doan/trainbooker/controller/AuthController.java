package com.doan.trainbooker.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.doan.trainbooker.dto.response.ApiResponse;
import com.doan.trainbooker.dto.response.BookerResponse;
import com.doan.trainbooker.service.services.BookerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

	BookerService bookerService;

	@PostMapping("/api/auth/login-google")
	public ApiResponse<BookerResponse> loginWithGoogle(@RequestBody Map<String, String> request) {
		try {
			String token = request.get("token");

			DecodedJWT decodedJWT = JWT.decode(token);
			String email = decodedJWT.getClaim("email").asString();
			String fullName = decodedJWT.getClaim("name").asString();

			BookerResponse booker = bookerService.findOrCreateBooker(email, fullName);

			return ApiResponse.<BookerResponse>builder().result(booker).build();
		} catch (Exception e) {
			throw new RuntimeException("Invalid Google Token", e);
		}
	}

	@PostMapping("/api/auth/logout")
	public ApiResponse<String> logout(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}

		return ApiResponse.<String>builder().message("User logged out successfully.").result("Logout successful")
				.build();
	}

//	@PostMapping("/api/auth/logout")
//    public ApiResponse<String> logout(HttpSession session) {
//        session.invalidate();  // Hủy session khi người dùng đăng xuất
//        return ApiResponse.<String>builder().result("User logged out successfully").build();
//    }

}

package com.socialmedia.controller;

import com.socialmedia.DTO.UserDTO;
import com.socialmedia.entity.User;
import com.socialmedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {

    private final UserService userService;

    @GetMapping("/profile")
    public String getProfilePage(Model model) {

        User authenticatedUser = userService.getAuthenticatedUser();

        // Add user data to the model, so Thymeleaf can access it
        model.addAttribute("user", authenticatedUser);

        // Render the profile.html template
        return "profile";
    }


    // Handle profile update
    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute("user") UserDTO userDTO) {
        userService.updateUserProfile(userDTO);
        return "redirect:/profile?success";
    }
}


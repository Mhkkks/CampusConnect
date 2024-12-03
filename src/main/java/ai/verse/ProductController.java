package ai.verse;


import ai.verse.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.stream.Collectors;

// Because I want to create API for posts I created Post controller
// Controller is helping us to create an API. Here we are API Producer or API Creator

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JavaMailSender mailSender;
    @GetMapping("/product/4")
    public ResponseEntity<List<ProductEntity>> getProduct() {
        List<ProductEntity> allProducts = productRepository.findAll();

           List<ProductEntity> category4Products = allProducts.stream()
                .filter(product -> product.getCategoryid() == 4)
                .collect(Collectors.toList());

           if (category4Products.isEmpty()) {
            System.out.println("No products found in category 4.");
        } else {
            System.out.println("Category 4 products: " + category4Products);
        }


        return new ResponseEntity<>(category4Products, HttpStatus.OK);
    }

       public ResponseEntity<String> updateProductQuantity(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        if (body.containsKey("quantity_select")) {
            int newQuantity = body.get("quantity_select");
            Optional<ProductEntity> productOptional = productRepository.findById(id);
            if (productOptional.isPresent()) {
                ProductEntity product = productOptional.get();
                product.setQuantity_select(newQuantity);
                productRepository.save(product);

                return new ResponseEntity<>("Product quantity updated successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Product not found.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Missing quantity data.", HttpStatus.BAD_REQUEST);
        }
    }



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;
    @PostMapping("/save-cart-summary")
    public ResponseEntity<String> saveCartSummary(@RequestBody Map<String, String> request) {

        Integer userId = null;
        try {
            userId = Integer.parseInt(request.get("userId"));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid User ID format.");
        }
        String cartSummary = request.get("cartSummary");

           if (userId == null || cartSummary == null || cartSummary.isEmpty()) {
            return ResponseEntity.badRequest().body("User ID and Cart Summary are required.");
        }

        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(userId);
        cartEntity.setCart_summary(cartSummary);
        cartRepository.save(cartEntity);

        return ResponseEntity.ok("Cart summary saved successfully.");
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required.");
        }


        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);


        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(email);
        userEntity.setPassword(otp);
        userRepository.save(userEntity);

         SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-email@example.com");
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);

        try {
            mailSender.send(message);
            return ResponseEntity.ok("OTP sent successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send OTP. Please try again later.");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserEntity loginDetails) {
        String email = loginDetails.getUsername();
        String password = loginDetails.getPassword();

        UserEntity user = userRepository.findByUsername(email);

        if (user != null && password.equals(user.getPassword())) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String enteredOtp = request.get("otp");

        if (email == null || enteredOtp == null) {
            return ResponseEntity.badRequest().body("Email and OTP are required.");
        }

        UserEntity userEntity = userRepository.findByUsername(email);
        if (userEntity != null && userEntity.getPassword().equals(enteredOtp)) {
            return ResponseEntity.ok("OTP verified successfully.");
        } else {
            return ResponseEntity.status(400).body("Invalid OTP.");
        }


    }
}


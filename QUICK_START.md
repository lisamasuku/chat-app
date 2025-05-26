# QuickChat - Quick Start Guide

## 🚀 Get Started in 5 Minutes

### Step 1: Prerequisites Check
Make sure you have:
- [ ] Java 11 or higher installed
- [ ] Maven installed (or use your IDE's built-in Maven)
- [ ] Git installed
- [ ] GitHub account created
- [ ] Applied for GitHub Student Developer Pack

### Step 2: Verify Your Setup
```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Check Git version
git --version
```

### Step 3: Initialize Git Repository
```bash
# Initialize git repository
git init

# Add all files
git add .

# Make initial commit
git commit -m "Initial project setup with Maven structure and CI/CD"

# Add your GitHub remote (replace with your repository URL)
git remote add origin https://github.com/YOUR_USERNAME/quickchat-app.git

# Push to GitHub
git push -u origin main
```

### Step 4: Start Development

#### Phase 1: Authentication System (Start Here!)
1. **Create the Login class**:
   ```bash
   # Create the main Login class file
   touch src/main/java/Login.java
   ```

2. **Create the test file**:
   ```bash
   # Create the test file
   touch src/test/java/LoginTest.java
   ```

3. **Start with Test-Driven Development**:
   - Write your tests first (see DEVELOPMENT_ROADMAP.md)
   - Then implement the methods to make tests pass

#### Your First Task: Username Validation
Create a method that validates usernames with these rules:
- Must contain an underscore (_)
- Must be 5 characters or less
- Return appropriate success/error messages

### Step 5: Run Tests
```bash
# Run all tests
mvn test

# Run tests with coverage report
mvn clean test jacoco:report

# View coverage report
# Open target/site/jacoco/index.html in your browser
```

### Step 6: Create Feature Branches
```bash
# Create a feature branch for authentication
git checkout -b feature-authentication

# Work on your code...
# Commit changes
git add .
git commit -m "Add username validation method"

# Push feature branch
git push origin feature-authentication

# Create pull request on GitHub
```

## 📋 Development Checklist

### Part 1: Authentication (Week 1-2)
- [ ] `checkUserName()` method
- [ ] `checkPasswordComplexity()` method  
- [ ] `checkCellPhoneNumber()` method (use AI for regex)
- [ ] `registerUser()` method
- [ ] `loginUser()` method
- [ ] `returnLoginStatus()` method
- [ ] All authentication unit tests
- [ ] Tests passing in CI/CD

### Part 2: Messaging (Week 3-4)
- [ ] `Message` class structure
- [ ] Menu system implementation
- [ ] Message validation methods
- [ ] Message hash generation
- [ ] JSON storage (use AI assistance)
- [ ] All messaging unit tests
- [ ] Integration with authentication

### Part 3: Data Management (Week 5-6)
- [ ] Array-based storage
- [ ] Search functionality
- [ ] Report generation
- [ ] Message deletion
- [ ] JSON file reading
- [ ] All data management tests

## 🧪 Testing Strategy

### Write Tests First!
1. Look at the exact test data in the requirements
2. Write the test method
3. Run the test (it should fail)
4. Implement the method to make it pass
5. Refactor if needed

### Example Test Structure:
```java
@Test
public void testUsernameValidation() {
    Login login = new Login();
    
    // Test valid username
    assertTrue(login.checkUserName("kyl_1"));
    
    // Test invalid username
    assertFalse(login.checkUserName("kyle!!!!!!!"));
}
```

## 🚨 Important Reminders

### AI Tool Usage
- Use AI for regex patterns and JSON handling
- **Always attribute AI-generated code**
- Example attribution:
  ```java
  /*
   * The following regular expression was generated using ChatGPT
   * OpenAI. (2024). ChatGPT (Version 4) [Large language model].
   * https://chat.openai.com/
   * Prompt: "Create a regex for South African phone numbers..."
   */
  ```

### Quality Standards
- ✅ All tests must pass before committing
- ✅ Use exact test data from requirements
- ✅ Follow Java naming conventions
- ✅ Add proper error handling
- ✅ Write clear, readable code

### Git Workflow
- ✅ Create feature branches for each part
- ✅ Commit frequently with descriptive messages
- ✅ Never push broken code to main
- ✅ Use pull requests for code review

## 🆘 Need Help?

### Common Issues
1. **Tests not running**: Check Maven setup and JUnit dependencies
2. **CI/CD failing**: Ensure all tests pass locally first
3. **Git issues**: Make sure you're on the correct branch
4. **Build errors**: Check Java version compatibility

### Resources
- [Maven Getting Started](https://maven.apache.org/guides/getting-started/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Git Branching Tutorial](https://learngitbranching.js.org/)

### Next Steps
1. Read through README.md for complete project overview
2. Check DEVELOPMENT_ROADMAP.md for detailed task breakdown
3. Start with Part 1: Authentication System
4. Write tests first, then implement
5. Commit and push regularly

**Good luck! Remember: Focus on quality over speed. It's better to have working, tested code than rushed, broken code.** 🎯 
# QuickChat Development Roadmap

## 🗺️ Project Timeline & Milestones

### Phase 1: Project Setup & Part 1 (Week 1-2)
**Goal**: Complete authentication system with full testing

#### Setup Tasks
- [ ] Create GitHub repository (private, in organization)
- [ ] Apply for GitHub Student Developer Pack
- [ ] Set up local development environment
- [ ] Initialize project structure
- [ ] Set up Maven/Gradle build system
- [ ] Configure JUnit testing framework

#### Part 1: Authentication System
- [ ] **Task 1.1**: Create `Login` class structure
- [ ] **Task 1.2**: Implement `checkUserName()` method
  - Username must contain underscore (_)
  - Maximum 5 characters
  - Return appropriate messages
- [ ] **Task 1.3**: Implement `checkPasswordComplexity()` method
  - Minimum 8 characters
  - Must contain: uppercase, number, special character
  - Return appropriate messages
- [ ] **Task 1.4**: Implement `checkCellPhoneNumber()` method (AI-assisted)
  - International format (+27)
  - Maximum 10 digits total
  - Use regular expressions
  - **AI Attribution Required**
- [ ] **Task 1.5**: Implement `registerUser()` method
  - Combine all validations
  - Return registration status
- [ ] **Task 1.6**: Implement `loginUser()` and `returnLoginStatus()` methods
  - Verify credentials
  - Return personalized welcome message

#### Testing Phase 1
- [ ] **Test 1.1**: Username validation tests
  - Valid: "kyl_1" → Success message
  - Invalid: "kyle!!!!!!" → Error message
- [ ] **Test 1.2**: Password complexity tests
  - Valid: "Ch&&sec@ke99!" → Success message
  - Invalid: "password" → Error message
- [ ] **Test 1.3**: Phone number validation tests
  - Valid: "+27838968976" → Success message
  - Invalid: "08966553" → Error message
- [ ] **Test 1.4**: Login functionality tests
  - Successful login → True + welcome message
  - Failed login → False + error message

### Phase 2: Messaging System (Week 3-4)
**Goal**: Complete message creation, validation, and storage

#### Part 2: Message Management
- [ ] **Task 2.1**: Create feature branch `git checkout -b feature-messaging`
- [ ] **Task 2.2**: Create `Message` class structure
- [ ] **Task 2.3**: Implement main application menu
  - Welcome message: "Welcome to QuickChat"
  - Option 1: Send Messages
  - Option 2: Show recent messages (Coming Soon)
  - Option 3: Quit
- [ ] **Task 2.4**: Implement message input system
  - User defines number of messages
  - Loop for message entry
- [ ] **Task 2.5**: Implement `checkMessageID()` method
  - Generate 10-digit random ID
  - Validate ID length
- [ ] **Task 2.6**: Implement `checkRecipientCell()` method
  - Reuse phone validation from Part 1
  - Maximum 10 characters
  - International format
- [ ] **Task 2.7**: Implement message validation
  - Maximum 250 characters
  - Return appropriate error/success messages
- [ ] **Task 2.8**: Implement `createMessageHash()` method
  - Format: `XX:Y:FIRSTLAST`
  - XX = first 2 digits of message ID
  - Y = message number
  - FIRSTLAST = first and last words (uppercase)
- [ ] **Task 2.9**: Implement `sentMessage()` method
  - Options: Send, Store, Disregard
  - Handle user choice
- [ ] **Task 2.10**: Implement JSON storage (AI-assisted)
  - Store messages to JSON file
  - **AI Attribution Required**
- [ ] **Task 2.11**: Implement message display (JOptionPane)
  - Show: MessageID, Hash, Recipient, Message
- [ ] **Task 2.12**: Implement `printMessages()` and `returnTotalMessages()`

#### Testing Phase 2
- [ ] **Test 2.1**: Message length validation
  - Success: ≤250 characters → "Message ready to send"
  - Failure: >250 characters → "Message exceeds X characters"
- [ ] **Test 2.2**: Recipient validation (reuse from Part 1)
- [ ] **Test 2.3**: Message hash generation
  - Test data: "Hi Mike, can you join us for dinner tonight"
  - Expected: "00:0:HITONIGHT"
- [ ] **Test 2.4**: Message ID generation
- [ ] **Test 2.5**: Message actions testing
  - Send → "Message successfully sent"
  - Disregard → "Press 0 to delete message"
  - Store → "Message successfully stored"

### Phase 3: Data Management & Reporting (Week 5-6) ✅ COMPLETED
**Goal**: Complete array management, search, and reporting features

#### Part 3: Advanced Features
- [x] **Task 3.1**: Create feature branch `git checkout -b feature-data-management`
- [x] **Task 3.2**: Implement array structures
  - Sent Messages array
  - Disregarded Messages array
  - Stored Messages array (from JSON)
  - Message Hash array
  - Message ID array
- [x] **Task 3.3**: Implement array population methods
- [x] **Task 3.4**: Implement search functionality
  - Search by Message ID
  - Search by recipient
  - Find longest message
- [x] **Task 3.5**: Implement message deletion by hash
- [x] **Task 3.6**: Implement comprehensive reporting
  - Display all sent messages
  - Include: Hash, Recipient, Message
- [x] **Task 3.7**: Integrate JSON reading (AI-assisted)
  - Read stored messages into array
  - **AI Attribution Required**

#### Testing Phase 3 ✅ COMPLETED
Use provided test data:
- [x] **Test 3.1**: Array population verification
- [x] **Test 3.2**: Longest message detection
  - Expected: "Where are you? You are late! I have asked you to be on time."
- [x] **Test 3.3**: Message ID search
  - Search for message 4 → "It is dinner time!"
- [x] **Test 3.4**: Recipient search
  - Search "+27838884567" → Return 2 messages
- [x] **Test 3.5**: Message deletion
  - Delete message 2 by hash → Success confirmation
- [x] **Test 3.6**: Report generation
  - Display all sent messages with full details

### Phase 4: CI/CD & Final Integration (Week 7)
**Goal**: Automate testing and finalize project

#### CI/CD Setup
- [ ] **Task 4.1**: Create GitHub Actions workflow
- [ ] **Task 4.2**: Configure automated testing
- [ ] **Task 4.3**: Set up build automation
- [ ] **Task 4.4**: Test workflow with sample commits

#### Final Integration
- [ ] **Task 4.5**: Merge all feature branches
- [ ] **Task 4.6**: Final testing suite execution
- [ ] **Task 4.7**: Code review and cleanup
- [ ] **Task 4.8**: Documentation completion
- [ ] **Task 4.9**: Final submission preparation

## 📊 Progress Tracking

### Completion Checklist
- [x] Part 1: Authentication (45 marks) ✅
- [x] Part 2: Messaging (65 marks) ✅  
- [x] Part 3: Data Management (85 marks) ✅
- [x] All unit tests passing (57/57 tests) ✅
- [ ] GitHub Actions configured
- [x] Code properly documented ✅
- [x] AI attributions included ✅

### Quality Gates
Before moving to next phase:
1. ✅ All tests for current phase pass
2. ✅ Code reviewed and cleaned
3. ✅ Feature branch merged to main
4. ✅ Documentation updated

## 🚨 Important Notes

### AI Tool Usage
- Use AI for regex patterns and JSON handling
- Always attribute AI-generated code
- Understand and modify AI suggestions
- Don't copy-paste without comprehension

### Testing Strategy
- Write tests BEFORE implementing features (TDD)
- Use exact test data provided in requirements
- Ensure 100% test coverage for all methods
- Automate test execution

### Version Control Best Practices
- Create feature branches for each part
- Commit frequently with descriptive messages
- Never push untested code to main
- Use pull requests for code review

### Common Pitfalls to Avoid
- ❌ Skipping unit tests
- ❌ Not following exact message formats
- ❌ Ignoring validation requirements
- ❌ Poor error handling
- ❌ Inadequate AI attribution

## 🎯 Success Metrics

### Technical Requirements
- All 15+ unit tests passing
- Code coverage >90%
- No compilation errors
- Proper exception handling

### Process Requirements
- Regular commits (daily)
- Feature branch workflow
- Automated testing pipeline
- Complete documentation

### Quality Requirements
- Clean, readable code
- Proper OOP design
- Consistent naming conventions
- Comprehensive error messages 
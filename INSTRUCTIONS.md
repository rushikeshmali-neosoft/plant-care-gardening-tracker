


INSTRUCTIONS :
==============

MUST FOLLOW :
-------------

• Use PERSONA_FRONTEND_DEV.md for developing frontend code.
• Use PERSONA_BACKEND_DEV.md for developing backend code and integrating it with frontend.

• No unnecessary comments
• No theoretical explanations
• No repeated content unnecessarly 
• Optimize for performance and clarity


CODE QUALITY:
-------------
• Test-Driven Developement (TDD)
• Write clean, production-ready code
• Follow SOLID principles
• Maintain consistent naming conventions
• Avoid deeply nested logic


TEST DRIVEN DEVELOPEMENT (TDD) : 
--------------------------------
• First write Test cases for each APIs or Functionality.
• Then write APIs that will pass those test cases.  
• Acceptance  rate is '>85%'
• Do not accept code if test cases A.R is less.
• Note down passed & failed cases and store its logs in a separate md file named 'TDD_REVIEW.md' (if files do not exist in class path the generate it)


PERFORMANCE:
------------
• Optimize for time and space complexity
• Avoid unnecessary object creation
• Minimize memory usage
• Use efficient data structures & algorithums
• Prevent redundant computations
• Use session or session factory cache for avoiding unnecessary db calls 

CODE SIZE CONTROL:
------------------
• No boilerplate code unless essential
• Avoid duplicate logic (DRY)
• Keep functions small and focused
• Remove unused variables/imports
• Avoid over-engineering

ERROR HANDLING:
--------------
• Handle edge cases explicitly
• Avoid silent failures
• Use proper exception handling
• Do not swallow exceptions
• Fail fast on critical errors

NULL & SAFETY:
--------------
• Ensure null safety
• Avoid NullPointerException
• Validate inputs early
• Use Optional only when meaningful
• Do not overuse null checks

READABILITY:
------------
• Use self-explanatory variable/method names
• Maintain logical flow
• Avoid magic numbers (use constants if needed)
• Keep code easy to scan

STRUCTURE:
----------
• Follow proper architecture (layered/clean)
• Separate concerns clearly
• Do not mix business logic with utility logic
• Keep methods single-responsibility

DEPENDENCY CONTROL:
-------------------
• Avoid unnecessary libraries
• Prefer standard library when possible
• Do not introduce heavy dependencies
• Keep code lightweight

SCALABILITY:
------------
• Design for future extension
• Avoid hard-coded limits
• Use abstractions where needed
• Keep system flexible

SECURITY:
---------
• Validate all external inputs
• Avoid exposing sensitive data
• Prevent common vulnerabilities (injection, etc.)
• Do not trust client-side data

CONSISTENCY:
------------
• Follow coding conventions strictly
• Maintain uniform coding style
• Do not mix patterns randomly

OUTPUT DISCIPLINE:
------------------
• Do exactly what is requested
• Do not add extra features
• Do not change unrelated code

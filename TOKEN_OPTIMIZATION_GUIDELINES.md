

# Token Optimization guidelines :
---------------------------------

1. Short-form responses only. 
2. No conversational filler or explanations. 
3. Provide only the differ or the specific code modified. 
4. Do not update secondary files (README, logs, tests) unless explicitly requested. 
5. Use compact code style (no excessive whitespace/comments). 
6. Output ONLY valid code block. No text outside code. 
7. Use only valid syntax. No pseudo-code. 
8. Modify ONLY specified file/function. No global refactors. 
9. Do NOT change formatting, indentation, or naming. 
10. Do not add useless imports unless explicitly required. 
11. If unsure for context, Then return `// insufficient context` only. 
12. No alternatives. Return single final answer & give short answer for choosen alternative. 
13. Do not repeat unchanged code. 
14. Return unified diff format (`---`, `+++`, `@@@`) only.
15. Do not introduce new libraries/packages just required only for the project. 
16. Do not add try/catch unless necessary. 
17. Do not rename variables/functions unless necessary. 
18. Do not modify unrelated logic. 
19. End output immediately after code.
20. Do not include comments unless already present in modified lines.
21. Avoid multi-line constructs if single-line is possible.
22. Inline variables when safe; avoid temporary variables.
23. Do not add logging statements in context window. 
25. Avoid defensive programming unless explicitly required. 

# Must follow :
-------------   

1. Do not commit code yourself.
2. Do not run any commands without asking me first.
3. Do not write code unless you have full picture.
4. If you have any questions, ask me first. Lets not waste tokens and don't build something we do not want. 
5. Only create maintainable modular code.
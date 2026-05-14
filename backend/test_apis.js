async function run() {
  const fs = require('fs');
  const path = require('path');

  console.log("Fetching OpenAPI spec...");
  const specRes = await fetch("http://localhost:8080/api-docs");
  const spec = await specRes.json();

  const paths = Object.keys(spec.paths);

  // Hardcode the user provided token
  const token = "77755";
  console.log("Using provided JWT token:", token);

  let successList = [];
  let errorList = [];

  for (const apiPath of paths) {
      const methods = Object.keys(spec.paths[apiPath]);
      for (const method of methods) {
          if (method === 'options' || method === 'head') continue;
          
          let url = `http://localhost:8080${apiPath}`;
          
          // replace path params with 1
          url = url.replace(/\{[^}]+\}/g, '1');
          
          let body = undefined;
          let headers = {
              'Accept': 'application/json'
          };
          if (token) {
              headers['Authorization'] = `Bearer ${token}`;
          }

          if (method === 'post' || method === 'put' || method === 'patch') {
              headers['Content-Type'] = 'application/json';
              body = JSON.stringify({}); // Send empty object as default dummy body
              
              const operation = spec.paths[apiPath][method];
              if (operation.requestBody && operation.requestBody.content && operation.requestBody.content['application/json']) {
                  const schema = operation.requestBody.content['application/json'].schema;
                  if (schema && schema.$ref) {
                      body = JSON.stringify({ name: "Dummy", title: "Dummy", description: "Dummy", status: "ACTIVE" });
                  }
              }
          }

          console.log(`Testing ${method.toUpperCase()} ${apiPath}...`);
          try {
              const res = await fetch(url, {
                  method: method.toUpperCase(),
                  headers: headers,
                  body: body
              });
              
              let resText = await res.text();
              let resObj = resText;
              try { resObj = JSON.parse(resText); } catch(e) {}
              
              const reportEntry = `
### ${method.toUpperCase()} ${apiPath}
- **Status Code**: \`${res.status} ${res.statusText}\`
- **Request URL**: \`${url}\`
- **Request Body**: \`${body || 'N/A'}\`
- **Response**:
\`\`\`json
${JSON.stringify(resObj, null, 2)}
\`\`\`
`;
              if (res.status >= 200 && res.status < 400) {
                  successList.push(reportEntry);
              } else {
                  errorList.push(reportEntry);
              }
          } catch (err) {
              const reportEntry = `
### ${method.toUpperCase()} ${apiPath}
- **Error**: \`${err.message}\`
- **Request URL**: \`${url}\`
- **Request Body**: \`${body || 'N/A'}\`
`;
              errorList.push(reportEntry);
          }
      }
  }

  const markdown = `# Full API Documentation (Using Token: ${token})

This document contains automated tests for all APIs found in the backend OpenAPI specification.

## ❌ APIs Responding with Errors
These APIs returned a 4xx or 5xx status code (or failed to connect).
${errorList.join('\n---\n')}

## ✅ Successful APIs
These APIs returned a 2xx or 3xx status code.
${successList.join('\n---\n')}
`;

  const outPath = path.join(__dirname, '..', 'API_DOC', 'all_apis_documentation.md');
  fs.writeFileSync(outPath, markdown);
  console.log(`Documentation updated at: ${outPath}`);
}

run().catch(console.error);

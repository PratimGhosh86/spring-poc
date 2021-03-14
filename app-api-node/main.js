const express = require("express");
const app = express();
const port = 9000;

app.use(express.json());

app.get("/", (req, res) => {
  res.send("Hello World!");
});

app.get("/health", (req, res) => {
  res.setHeader("Content-Type", "application/json");
  res.end(JSON.stringify({ status: "UP" }));
});

app.get("/info", (req, res) => {
  res.setHeader("Content-Type", "application/json");
  res.end(JSON.stringify({ info: "Information" }));
});

app.listen(port, () => {
  console.log(`App listening at http://localhost:${port}`);
});

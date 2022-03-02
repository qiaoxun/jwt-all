/* eslint-disable no-console */
const express = require('express');

const app = express();
const port = 3000;

app.use(express.static('./'));

app.get('/', (req, res) => {
  res.send('Hello World!');
});

app.get('/flow-definition/list', (req, res) => {
  const listData = new Array(20).fill(1).map((item, index) => {
    return {
      id: index + 1,
      name: `Follow${index + 1}`,
      description: `Follow ${index + 1} description`,
    };
  });
  res.json(listData);
});

app.get('/flow-definition/:id', (req, res) => {
  const { id } = req.params;
  res.json({
    id,
    name: `Follow${id}`,
    description: `Follow ${id} description`,
  });
});

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`);
});

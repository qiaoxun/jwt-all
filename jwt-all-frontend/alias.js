const path = require('path');

// Used by
// - ./webpack.config.js
// - ./babel.config.js

// For VSCode, see ./jsconfig.json

module.exports = {
  component: path.resolve(__dirname, './src/component'),
};

module.exports = {
  parser: '@babel/eslint-parser',
  env: {
    node: true,
    browser: true,
    jest: true,
    es6: true,
  },
  plugins: ['react', 'react-hooks'],
  // Note that prettier only turns off all rules that are unnecessary or might conflict with Prettier
  extends: [
    'eslint:recommended',
    'plugin:import/recommended',
    'plugin:react/recommended',
    'airbnb',
    'prettier',
  ],
  rules: {
    indent: ['error', 2],
    quotes: ['error', 'single', { avoidEscape: true, allowTemplateLiterals: true }],
    semi: 'error',
    'jsx-quotes': ['error', 'prefer-double'],
    'no-console': ['error', { allow: ['warn', 'error'] }],
    'no-alert': 'error',
    'keyword-spacing': ['error', { before: true, after: true }],
    'space-before-blocks': 'error',
    'func-names': 'error',
    'brace-style': ['error', '1tbs'],
    'comma-dangle': 'error',
    'no-bitwise': 'off',
    'no-restricted-syntax': ['error', 'WithStatement'],
    'semi-spacing': ['error', { before: false, after: true }],
    'comma-spacing': 'error',
    'no-underscore-dangle': 'error',
    'arrow-body-style': 'off',
    'no-restricted-exports': 'off',
    'import/no-extraneous-dependencies': 'off',

    // Override jsx-a11y
    'jsx-a11y/mouse-events-have-key-events': 'off',
    // airbnb require both element as label children and has `htmlFor`,
    // i.e. <label htmlFor="name"><input id="name /></label>,
    // `either` only require one of them.
    'jsx-a11y/label-has-associated-control': ['error', { assert: 'either' }],
    'jsx-a11y/no-autofocus': 'off',
    'jsx-a11y/no-static-element-interactions': 'off',
    'jsx-a11y/no-noninteractive-element-interactions': 'off',
    'jsx-a11y/click-events-have-key-events': 'off',

    // React and jsx
    // airbnb use .jsx
    'react/jsx-filename-extension': ['error', { extensions: ['.js'] }],
    // airbnb set all to 'parens-new-line', but closed by prettier.
    'react/jsx-wrap-multilines': [
      'error',
      {
        declaration: 'parens-new-line',
        assignment: 'parens-new-line',
        return: 'parens-new-line',
        arrow: 'parens-new-line',
        condition: 'parens-new-line',
        logical: 'parens-new-line',
        // set `prop` to `ignore` because there is conflict between eslint and prettier.
        prop: 'ignore',
      },
    ],
    'react/require-default-props': 'off',
    'react/jsx-boolean-value': ['error', 'always'],
    'react/button-has-type': 'off',
    'react/no-array-index-key': 'off',
    'react/no-danger': 'error',
    'react/state-in-constructor': 'off',

    'react-hooks/rules-of-hooks': 'error',
    'react-hooks/exhaustive-deps': 'error',
  },
  settings: {
    'import/resolver': 'webpack',
    react: {
      version: 'detect',
    },
  },
};

const { merge } = require('webpack-merge');
const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const ESLintPlugin = require('eslint-webpack-plugin');
const StyleLintPlugin = require('stylelint-webpack-plugin');

const devMode = process.env.npm_lifecycle_event === 'start';

const common = {
  entry: path.resolve(__dirname, 'src/index.js'),
  output: {
    path: path.resolve(__dirname, 'build'),
    publicPath: '/',
    filename: devMode ? 'js/[name].bundle.js' : 'js/[name].[contenthash:8].bundle.js',
    chunkFilename: devMode ? 'js/[name].bundle.js' : 'js/[name].[contenthash:8].bundle.js',
    assetModuleFilename: devMode
      ? 'media/[name].bundle.js'
      : 'media/[name].[contenthash:8].bundle.js',
  },
  module: {
    rules: [
      {
        test: /\.jsx?$/,
        exclude: /node_modules/,
        use: ['thread-loader', 'babel-loader'],
      },
      {
        test: /\.css$/,
        use: [
          devMode ? 'style-loader' : MiniCssExtractPlugin.loader,
          'css-loader',
          'postcss-loader',
        ],
      },
      {
        test: /\.(png|jpe?g|gif)$/,
        type: 'asset',
        generator: {
          filename: 'img/[name][hash][ext][query]',
        },
      },
      {
        test: /\.svg$/,
        use: [
          {
            loader: '@svgr/webpack',
            options: {
              prettier: false,
              svgo: false,
              svgoConfig: {
                plugins: [{ removeViewBox: false }],
              },
              titleProp: true,
              ref: true,
            },
          },
          {
            loader: 'file-loader',
            options: {
              name: 'img/[name].[hash].[ext]',
            },
          },
        ],
      },
    ],
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: path.resolve(__dirname, 'public/index.html'),
      favicon: path.resolve(__dirname, 'public/favicon.ico'),
    }),
  ],
  devtool: 'source-map',
};

module.exports = () => {
  if (devMode) {
    return merge(common, {
      mode: 'development',
      devServer: {
        port: 9090,
        hot: true,
        open: true,
        historyApiFallback: true,
        proxy: [
          {
            context: ['/auth', '/user'],
            target: 'http://localhost:8081',
          },
        ],
      },
      stats: 'minimal',
    });
  }

  return merge(common, {
    mode: 'production',
    plugins: [
      new ESLintPlugin({
        extensions: ['js', 'jsx'],
        exclude: ['node_modules'],
      }),
      new StyleLintPlugin({
        files: ['./src/!**!/!*.css'],
      }),
      new MiniCssExtractPlugin({
        filename: 'css/[name].[contenthash:8].bundle.css',
      }),
      new CleanWebpackPlugin(),
    ],
  });
};

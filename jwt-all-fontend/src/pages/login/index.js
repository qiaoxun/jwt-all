import './index.css';
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button, Form, Input, Checkbox } from 'antd';
import 'antd/dist/antd.css';
import jwt_decode from "jwt-decode";
import client from '../../service/client';

function Login() {
  const navigate = useNavigate();

  const onFinish = values => {
    const { username, password } = values;
    const url = '/auth/login';
    const formData = {
      username,
      password,
    };
    
    client.post(url, formData).then(data => {
      const payload = jwt_decode(data);
      window.localStorage.setItem('tokenData', payload);
      window.localStorage.setItem('token', data);
      navigate('/');
    });
  };

  const onFinishFailed = errorInfo => {
    console.log('Failed:', errorInfo);
  };

  return (
    <div className="login-page">
      <div className="login-box">
        <div className="illustration-wrapper">
          <img src="https://mixkit.imgix.net/art/preview/mixkit-left-handed-man-sitting-at-a-table-writing-in-a-notebook-27-original-large.png?q=80&auto=format%2Ccompress&h=700" alt="Login"/>
        </div>
        <Form
          name="login-form"
          initialValues={{ remember: true }}
          onFinish={onFinish}
          onFinishFailed={onFinishFailed}
        >
          <p className="form-title">Welcome</p>
          <p>Login to the JWT Demo System</p>
          <Form.Item
            name="username"
            rules={[{ required: true, message: 'Please input your username!' }]}
          >
            <Input placeholder="Username" />
          </Form.Item>

          <Form.Item
            name="password"
            rules={[{ required: true, message: 'Please input your password!' }]}
          >
            <Input.Password placeholder="Password" />
          </Form.Item>

          <Form.Item name="remember" valuePropName="checked">
            <Checkbox>Remember me</Checkbox>
          </Form.Item>

          <Form.Item>
            <Button type="primary" htmlType="submit" className="login-form-button">
              LOGIN
            </Button>
          </Form.Item>
        </Form>
      </div>
    </div>
  );
}

export default Login;

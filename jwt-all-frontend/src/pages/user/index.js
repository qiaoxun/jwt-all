import './index.css';
import React from 'react';
import { Table, Layout, Menu, Breadcrumb } from 'antd';
import 'antd/dist/antd.css';
import client from '../../service/client';

function User() {
  const { Header, Content, Footer } = Layout;

  const [listData, setListData] = React.useState([]);

  React.useEffect(() => {
    const url = '/user/list';
    // TODO wrap axios
    client.get(url).then((data) => {
      setListData(data);
    });
  });

  
  const columns = [
    {
      title: 'Username',
      dataIndex: 'username',
      key: 'username',
    },
    {
      title: 'Email',
      dataIndex: 'email',
      key: 'email',
    },
    {
      title: 'Password',
      dataIndex: 'password',
      key: 'password',
    },
  ];

  return (
    <Layout className="layout">
      <Header>
        <div className="logo" />
        <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['2']}>
          <Menu.Item key="user">User</Menu.Item>
        </Menu>
      </Header>
      <Content style={{ padding: '0 50px' }}>
        <Breadcrumb style={{ margin: '16px 0' }}>
          <Breadcrumb.Item>User</Breadcrumb.Item>
        </Breadcrumb>
        <div className="site-layout-content">
          <Table dataSource={listData} columns={columns} />
        </div>
      </Content>
      <Footer style={{ textAlign: 'center' }}>JWT Demo</Footer>
    </Layout>
  );
}

export default User;

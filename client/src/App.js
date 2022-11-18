import Header from './components/Header';
import { Routes, Route } from 'react-router-dom';
import ArticleDetail from './pages/ArticleDetail';
import ArticleEdit from './pages/ArticleEdit';
import ArticleWrite from './pages/ArticleWrite';
import Main from './pages/Main';
import MyPage from './pages/MyPage';
import MyPageEdit from './pages/MyPageEdit';
import Profile from './pages/Profile';
import SignUp from './pages/SignUp';
import styled from 'styled-components';

const HeaderContainer = styled.div`
  height: 62px;
`;

function App() {
  return (
    <>
      <HeaderContainer>
        <Header />
      </HeaderContainer>
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/write" element={<ArticleWrite />} />
        <Route path="/article/edit/:id" element={<ArticleEdit />} />
        <Route path="/article/:id" element={<ArticleDetail />} />
        <Route path="/mypage/:id" element={<MyPage />} />
        <Route path="/mypage/edit/:id" element={<MyPageEdit />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/signup" element={<SignUp />} />
      </Routes>
    </>
  );
}

export default App;

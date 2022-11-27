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

const Container = styled.div`
  background-color: var(--purple-light);
`;

const ContentContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin: 0 170px;
`;

function App() {
  return (
    <>
      <Container>
        <Header />
        <ContentContainer>
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
        </ContentContainer>
      </Container>
    </>
  );
}

export default App;

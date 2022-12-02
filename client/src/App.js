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
import ReceiveGithub from './pages/ReceiveGithub';
import styled from 'styled-components';

const Container = styled.div`
  background-color: var(--purple-light);
  min-width: fit-content;
  width: 100%;
`;

const ContentContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin: 0 170px;
  min-width: fit-content;
  @media screen and (max-width: 830px) {
    margin: 0 50px;
  }
  @media screen and (max-width: 500px) {
    margin: 0 15px;
  }
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
            <Route path="/articles/edit/:id" element={<ArticleEdit />} />
            <Route path="/articles/:id" element={<ArticleDetail />} />
            <Route path="/mypage/:id" element={<MyPage />} />
            <Route path="/mypage/edit/:id" element={<MyPageEdit />} />
            <Route path="/profile/:id" element={<Profile />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/receive-token.html" element={<ReceiveGithub />} />
          </Routes>
        </ContentContainer>
      </Container>
    </>
  );
}

export default App;

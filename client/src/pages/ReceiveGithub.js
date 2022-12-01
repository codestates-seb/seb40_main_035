import { useEffect } from 'react';
import styled from 'styled-components';
import { AiOutlineCheck } from 'react-icons/ai';
import DefaultButton from '../components/DefaultButton';

const Container = styled.div`
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
  bottom: 0;
  z-index: 30;
  padding-top: 100px;
  text-align: center;
  background-color: var(--purple-light);
  color: var(--purple);

  .check-icon {
    width: 60px;
    height: 60px;
    margin-bottom: 40px;
    padding: 15px 10px 10px;
    border-radius: 50%;
    background-color: var(--purple);
  }

  p {
    margin-bottom: 40px;
  }
`;

const ReceiveGithub = () => {
  useEffect(() => {
    const url = new URL(window.location.href);
    const githubURL = url.searchParams.get('github');
    if (githubURL) {
      localStorage.setItem('githubURL', githubURL);
      window.close();
      return githubURL;
    }
  }, []);

  return (
    <Container>
      <AiOutlineCheck className="check-icon" fill="#fff" />
      <p>
        깃허브 인증이 완료되었습니다!
        <br />
        페이지로 돌아가주세요.
      </p>
      <DefaultButton text="돌아가기" onClick={() => window.close()} />
    </Container>
  );
};

export default ReceiveGithub;

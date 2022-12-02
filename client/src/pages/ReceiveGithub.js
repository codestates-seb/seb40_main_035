/* eslint-disable no-unused-vars */
import { useEffect, useState } from 'react';
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

  .check-icon {
    width: 60px;
    height: 60px;
    margin-bottom: 40px;
    padding: 15px 10px 10px;
    border-radius: 50%;
    background-color: var(--purple);
  }

  h1 {
    margin-bottom: 10px;
    font-size: 20px;
    color: var(--black);
  }

  p {
    margin-bottom: 40px;
    color: var(--purple);
  }
`;

const ReceiveGithub = () => {
  const [time, setTime] = useState(3);

  const url = new URL(window.location.href);
  const githubURL = url.searchParams.get('github');
  const Authorization = url.searchParams.get('Authorization');
  const Refresh = url.searchParams.get('Refresh');
  const memberId = url.searchParams.get('memberId');

  if (githubURL) localStorage.setItem('githubURL', githubURL);
  else if (Authorization) {
    localStorage.setItem('Authorization', Authorization);
    localStorage.setItem('Refresh', Refresh);
    localStorage.setItem('memberId', memberId);
  }

  useEffect(() => {
    if (time === 0) window.close();
    setInterval(() => {
      setTime(time - 1);
    }, 1000);
  }, [time]);

  return (
    <Container>
      <AiOutlineCheck className="check-icon" fill="#fff" />
      <h1>
        깃허브 인증이 완료되었습니다!
        <br />
        페이지로 돌아가주세요.
      </h1>
      <p>
        <strong>{time}</strong>초 후 자동으로 돌아갑니다.
      </p>
      <DefaultButton text="돌아가기" onClick={() => window.close()} />
    </Container>
  );
};

export default ReceiveGithub;

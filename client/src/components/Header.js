import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { BiMoon } from 'react-icons/bi';
import { useState } from 'react';

const HeaderContainer = styled.header`
  z-index: 1;
  width: 100%;
  min-width: fit-content;
  height: 62px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: sticky;
  top: 0;
  right: 0;
  left: 0;

  z-index: 2;
  position: sticky;
  top: 0;
  right: 0;
  left: 0;

  .header-container {
    width: 100%;
    padding: 13px;
    max-width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    background-color: var(--purple);
  }
  a {
    text-decoration-line: none;
    color: var(--purple-light);
  }
  .gnb-container-left {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .gnb-container-right {
    display: flex;
    align-items: center;
    padding-right: 132px;
  }
  .logo {
    white-space: nowrap;
    padding-right: 50px;
    padding-left: 170px;
    font-size: 25px;
  }
  .gnb {
    display: flex;
    color: var(--purple-light);
    font-weight: 700;
    font-size: 15px;

    li {
      white-space: nowrap;
      list-style: none;
      margin-right: 31px;
      width: auto;
      height: auto;
      padding: 7px;
      text-align: center;

      border-radius: 14px;
      transition: box-shadow 300ms ease-in-out, color 300ms ease-in-out;
      :hover {
        box-shadow: 0 0 40px 40px rgba(81, 56, 144, 0.8) inset;
      }
    }
  }
`;

const NavBtn = styled.button`
  outline: none;
  background-color: transparent;
  border: none;
  color: ${(props) => (props.$color ? 'white' : '#E2E2E2')};
  font-weight: ${(props) => (props.$color ? 700 : 500)};
  font-size: 15px;
`;

const Header = () => {
  const [menu, setMenu] = useState(0);
  const navigate = useNavigate();

  const onMain = () => {
    navigate('/');
    setMenu(0);
  };

  const onAbout = () => {
    navigate('/about');
    setMenu(1);
  };

  const onWrite = () => {
    navigate('/write');
    setMenu(2);
  };
  const onLogin = () => {
    navigate('/login');
    setMenu(3);
  };
  const onSignup = () => {
    navigate('/signup');
    setMenu(4);
  };

  return (
    <HeaderContainer>
      <div className="header-container">
        <div className="gnb-container-left">
          <NavBtn onClick={onMain}>
            <h1 className="logo">삼삼오오 </h1>
          </NavBtn>

          <ul className="gnb">
            <li>
              <NavBtn $color={menu === 1} onClick={onAbout}>
                사이트 소개
              </NavBtn>
            </li>
            <li>
              <NavBtn $color={menu === 0} onClick={onMain}>
                프로젝트
              </NavBtn>
            </li>
          </ul>
        </div>
        <div className="gnb-container-right">
          <ul className="gnb">
            <li>
              <NavBtn $color={menu === 2} onClick={onWrite}>
                글쓰기
              </NavBtn>
            </li>
            <li>
              <NavBtn $color={menu === 3} onClick={onLogin}>
                로그인
              </NavBtn>
            </li>
            <li>
              <NavBtn $color={menu === 4} onClick={onSignup}>
                회원가입
              </NavBtn>
            </li>
            <li>
              {/* 다크모드 후순위 작업 */}
              <BiMoon className="moon-icon" />
            </li>
          </ul>
        </div>
      </div>
    </HeaderContainer>
  );
};

export default Header;

import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { BiMoon } from 'react-icons/bi';
import { useState, useRef, useEffect } from 'react';
import LogIn from '../pages/LogIn';
import { useRecoilState, useSetRecoilState } from 'recoil';
import {
  inputTitleCheckState,
  interestsCheckState,
  skillstacksCheckState,
  startDateCheckState,
  endDateCheckState,
  feNumberCheckState,
  beNumberCheckState,
  inputBodyCheckState,
  hashtagsCheckState,
  modalOpenState,
  currentUserState,
} from '../atom/atom';
import { notiSuccess } from '../assets/toast';

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
  z-index: 25;

  .header-container {
    width: 100%;
    padding: 13px;
    max-width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    background-color: var(--purple);

    @media screen and (max-width: 1020px) {
      justify-content: center;
    }
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
    @media screen and (max-width: 1020px) {
      padding-right: 0px;
    }
  }
  .logo {
    white-space: nowrap;
    padding-right: 50px;
    padding-left: 170px;
    font-size: 25px;

    @media screen and (max-width: 1020px) {
      padding-left: 0px;
    }
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
      @media screen and (max-width: 720px) {
        margin-right: 10px;
      }
    }

    button {
      @media screen and (max-width: 720px) {
        font-size: 0.8rem;
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
  const [modalOpen, setModalOpen] = useRecoilState(modalOpenState);
  const navigate = useNavigate();
  const setInputTitleCheck = useSetRecoilState(inputTitleCheckState);
  const setInterestsCheck = useSetRecoilState(interestsCheckState);
  const setSkillstacksCheck = useSetRecoilState(skillstacksCheckState);
  const setStartDateCheck = useSetRecoilState(startDateCheckState);
  const setEndDateCheck = useSetRecoilState(endDateCheckState);
  const setFeNumberCheck = useSetRecoilState(feNumberCheckState);
  const setBeNumberCheck = useSetRecoilState(beNumberCheckState);
  const setInputBodyCheck = useSetRecoilState(inputBodyCheckState);
  const setHashtagsCheck = useSetRecoilState(hashtagsCheckState);
  const [currentUser, setCurrentUser] = useRecoilState(currentUserState);

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
    setInputTitleCheck(true);
    setInterestsCheck(true);
    setSkillstacksCheck(true);
    setStartDateCheck(true);
    setEndDateCheck(true);
    setFeNumberCheck(true);
    setBeNumberCheck(true);
    setInputBodyCheck(true);
    setHashtagsCheck(true);
  };
  const onLogIn = () => {
    setModalOpen(!modalOpen);
    setMenu(3);
  };

  const onMyPage = () => {
    navigate(`/mypage/${currentUser.memberId}`);
    setMenu(3);
  };

  const onSignup = () => {
    navigate('/signup');
    setMenu(4);
  };

  const onLogOut = () => {
    localStorage.removeItem('Authorization');
    localStorage.removeItem('Refresh');
    localStorage.removeItem('memberId');
    setCurrentUser({ memberId: null, isLogIn: false });
    notiSuccess('로그아웃 되었습니다.');
    navigate('/');
    setMenu(4);
  };

  const userMenu = useRef();

  const modalCloseHandler = ({ target }) => {
    if (!userMenu.current.contains(target)) setModalOpen(false);
  };

  useEffect(() => {
    window.addEventListener('mousedown', modalCloseHandler);
    return () => {
      window.removeEventListener('mousedown', modalCloseHandler);
    };
  });

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
            {currentUser.isLogIn ? (
              <>
                <li>
                  <NavBtn $color={menu === 3} onClick={onMyPage}>
                    마이페이지
                  </NavBtn>
                </li>
                <li>
                  <NavBtn $color={menu === 4} onClick={onLogOut}>
                    로그아웃
                  </NavBtn>
                </li>
              </>
            ) : (
              <>
                <li>
                  <NavBtn $color={menu === 3} onClick={onLogIn}>
                    로그인
                  </NavBtn>
                </li>
                <li>
                  <NavBtn $color={menu === 4} onClick={onSignup}>
                    회원가입
                  </NavBtn>
                </li>{' '}
              </>
            )}
            <li>
              {/* 다크모드 후순위 작업 */}
              <BiMoon className="moon-icon" />
            </li>
          </ul>
        </div>
      </div>
      {modalOpen ? <LogIn userMenu={userMenu} /> : null}
    </HeaderContainer>
  );
};

export default Header;

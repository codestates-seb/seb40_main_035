import styled from 'styled-components';
import { useRecoilState, useSetRecoilState } from 'recoil';
import { inputHashTagsState, hashtagsCheckState } from '../atom/atom';
import { RiDeleteBack2Fill } from 'react-icons/ri';

const Container = styled.div`
  width: 100%;
  height: auto;

  .content {
    .hashtag-list {
      height: 50px;
      display: flex;
      flex-wrap: wrap;
    }

    .hashtag {
      border: 1px solid var(--purple);
      border-radius: 25px;
      background-color: var(--purple);
      color: white;
      font-size: 15px;

      white-space: nowrap;
      display: flex;
      align-items: center;
      margin: 0 10px 10px 0;
      padding: 7px 10px;
    }

    .delete {
      margin: 2px 0 0 5px;
      display: flex;
      align-items: center;
      cursor: pointer;
    }
  }
`;

const InputHashTagBox = styled.input`
  width: 100%;
  height: 100%;
  padding: 8px 8px 10px;
  border-radius: 8px;
  border: none;
  color: var(--grey-dark);
  outline: none;
  border: 1px solid var(--purple-medium);
  transition: 300ms ease-in-out;
  white-space: nowrap;
  font-size: 16px;

  &:hover,
  &:focus,
  &:active {
    border-color: var(--purple);
  }

  &:focus,
  &:active {
    box-shadow: 0px 0px 0px 4px var(--purple-medium);
  }
`;

const Placeholder = styled.div`
  color: lightgray;
`;

const InputHashTag = () => {
  const [inputHashTags, setInputHashTags] = useRecoilState(inputHashTagsState);
  const setHashtagsCheck = useSetRecoilState(hashtagsCheckState);

  const onInputKeyUp = (e) => {
    const filterTarget = inputHashTags.filter(
      (el) => el.hashtagName === e.target.value,
    );
    // 중복 확인 위한 arr(중복X:null, 중복:값O)

    if (
      e.key === 'Enter' &&
      filterTarget.length === 0 &&
      e.target.value.length > 0
    ) {
      let hashTagObj = {};
      hashTagObj.hashtagName = e.target.value;
      setInputHashTags([...inputHashTags, hashTagObj]); // 해시태그가 추가된 arr를 해시태그리스트 값으로 재설정
      e.target.value = '';

      setHashtagsCheck(true);
    } else if (
      filterTarget.length !== 0 &&
      e.key === 'Enter' // 중복 태그 시
    ) {
      e.target.value = '';
    }

    if (e.keyCode === 32) {
      alert('해시태그는 공백 입력이 불가합니다.');
      e.target.value = '';
    }
  };

  const onDeleteClick = (index) => {
    const filtered = inputHashTags.filter((el, idx) => idx !== index);
    setInputHashTags(filtered);

    if (inputHashTags.length - 1 === 0) {
      setHashtagsCheck(false);
    }
  };

  return (
    <Container>
      <div className="content">
        <ul className="hashtag-list">
          {
            // 선택, 입력된 태그들 표시
            inputHashTags.length !== 0 ? (
              inputHashTags.map((tag, idx) => (
                <li key={idx} className="hashtag">
                  <div>#{tag.hashtagName}</div>
                  <div
                    className="delete"
                    onClick={() => onDeleteClick(idx)}
                    aria-hidden="true"
                  >
                    <RiDeleteBack2Fill />
                  </div>
                </li>
              ))
            ) : (
              <Placeholder>태그가 표시됩니다</Placeholder>
            )
          }
        </ul>

        <InputHashTagBox
          className="input-hashtag"
          placeholder="해시 태그 입력란입니다."
          onKeyUp={onInputKeyUp}
        />
      </div>
    </Container>
  );
};

export default InputHashTag;

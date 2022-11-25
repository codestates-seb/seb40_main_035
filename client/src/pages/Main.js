import ArticleCard from '../components/ArticleCard';
import styled from 'styled-components';
import { TbFilter } from 'react-icons/tb';
import { useRecoilState } from 'recoil';
import { articlesListState, selectedSkillstacksState } from '../atom/atom';
import { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import SwitchToggle from '../components/SwitchToggle';
import SkillStackSelect from '../components/SkillStackSelect';
import ScrollTopButton from '../components/ScrollTopButton';
import ArticlesGrid from '../components/ArticlesGrid';
import CloseButton from '../components/CloseButton';
import ExtendedButton from '../components/ExtendedButton';

const Container = styled.div`
  min-height: calc(100vh - 62px);
  width: 100%;

  h1 {
    padding: 67px 0 77px;
    border-bottom: 1px solid var(--purple-medium);
  }

  .view-options {
    text-align: right;
    position: relative;
    margin: 20px 0 32px;

    .filter-options {
      display: flex;
      justify-content: flex-end;
    }

    .sort-options {
      margin: 30px 0 -5px auto;

      button {
        font-size: 15px;
        font-weight: 700;
        color: var(--grey-dark);
        margin: 5px;
        cursor: pointer;
        background-color: transparent;
        border: none;

        &.active-option {
          color: var(--purple);
        }

        &:not(:last-child)::after {
          content: '|';
          margin-left: 11px;
          color: var(--purple-medium);
        }
      }
    }
  }
`;

const FilterSelectorWrapper = styled.div`
  .filter-modal-toggle {
    height: 36px;
    margin-left: 10px;
    padding-left: 4px;

    background-color: var(--purple-light);
    border: solid 2px var(--purple);
    border-radius: 20px;
    padding: 0 0.9em;

    font-weight: 700;
    font-size: 13px;
    color: var(--purple);

    transition: 300ms ease-in-out;

    display: flex;
    align-items: center;
    justify-content: center;

    &.opened {
      background-color: var(--purple-medium);
    }

    svg {
      stroke: var(--purple);
      margin-right: -3px;
    }
  }

  .filter-selector-modal {
    width: 695px;
    max-width: 100%;
    margin-top: 10px;
    position: absolute;
    right: 0;
    z-index: 1;
    background-color: #fff;
    border: 1px solid var(--purple);
    border-radius: 8px;

    .filter-contents {
      margin: 24px;

      p {
        margin-bottom: 22px;
        text-align: left;
        font-weight: 700;
        font-size: 15px;
        color: var(--black);
      }
    }

    .close-btn {
      position: absolute;
      top: 12px;
      right: 12px;
    }
  }
`;

const SkillFilterSelector = ({ setSkillFilter, setModalDisplay, isOpened }) => {
  const [selectedSkillStacks, setSelectedSkillStacks] = useRecoilState(
    selectedSkillstacksState,
  );
  const [isFiltered, setIsFiltered] = useState(false);

  const onToggle = () => {
    setModalDisplay();
    if (!isFiltered) setSelectedSkillStacks([]);
  };

  const onApply = () => {
    setSkillFilter(
      selectedSkillStacks.map((el) => {
        return el.name;
      }),
    );
    setIsFiltered(true);
  };

  return (
    <FilterSelectorWrapper>
      <button
        className={
          isOpened ? 'filter-modal-toggle opened' : 'filter-modal-toggle'
        }
        onClick={() => onToggle()}
      >
        필터링
        <TbFilter size={23} />
      </button>
      {isOpened && (
        <div className="filter-selector-modal">
          <div className="filter-contents">
            <p>기술 스택으로 필터를 설정해보세요!</p>
            <SkillStackSelect />
          </div>
          <ExtendedButton
            text="적용하기"
            onClick={() => onApply()}
            className="apply-btn"
          />
          <CloseButton className="close-btn" onClick={() => onToggle()} />
        </div>
      )}
    </FilterSelectorWrapper>
  );
};

const Loading = styled.div`
  width: 100%;
  height: 200px;
  padding-top: 100px;
  text-align: center;
  animation: blink 2s ease-in-out infinite;

  @keyframes blink {
    50% {
      opacity: 0;
    }
  }
`;

const Main = () => {
  // 아티클 목록
  const [articlesList, setArticlesList] = useRecoilState(articlesListState);
  // 전체보기/모집중만 보기 선택 토글
  const [viewAllStatus, setViewStatus] = useState(true);
  // 스킬 스택 필터
  const [filterModalDisplay, setFilterModalDisplay] = useState(false);
  const [skillfilter, setSkillFilter] = useState([]);
  // 정렬 옵션
  const sortOptions = {
    최신순: '',
    좋아요순: 'heart',
    조회순: 'view',
  };
  const [sortOption, setSortOption] = useState('최신순');
  // 데이터 로딩 상태
  const [isFetching, setIsFetching] = useState(false);
  // 데이터 요청 옵션
  const [pageNumber, setPageNumber] = useState(1);
  const [hasNextPage, setHasNextPage] = useState(true);
  const pageSize = 3 * Math.floor((visualViewport.width - 340) / (323 + 30));

  // 스크롤 감지
  useEffect(() => {
    const handleScroll = () => {
      const { scrollTop, offsetHeight } = document.documentElement;
      if (window.innerHeight + scrollTop >= offsetHeight) {
        setIsFetching(true);
      }
    };
    setIsFetching(true);
    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  // 필터링/정렬 변화
  useEffect(() => {
    setArticlesList([]);
    setPageNumber(1);
    setIsFetching(true);
    setHasNextPage(true);
  }, [viewAllStatus, skillfilter, sortOption]);

  // 데이터 요청 실행
  useEffect(() => {
    if (isFetching && hasNextPage) fetchArticles();
    else if (!hasNextPage) setIsFetching(false);
  }, [isFetching]);

  // 데이터 요청 콜백
  const fetchArticles = useCallback(async () => {
    // console.log('recoil state', articlesList);

    const skill = skillfilter.join(',');
    const status = viewAllStatus ? '' : false;
    const sort = sortOptions[sortOption];

    const { data } = await axios.get('/articles?', {
      params: { skill, status, page: pageNumber, sort, size: pageSize },
    });

    setArticlesList(articlesList.concat(data.data));
    setPageNumber(data.pageInfo.page + 1);
    setHasNextPage(data.pageInfo.totalPages !== data.pageInfo.page);
    setIsFetching(false);
    // console.log('FETCH DATA!');
    // console.log('states:', viewAllStatus, skillfilter, sortOption, pageNumber);
  }, [viewAllStatus, skillfilter, sortOption, pageNumber]);

  // console.log('PAINT!');
  return (
    <Container>
      <h1>
        삼삼오오에서 사이드 프로젝트를 함께 할<br />
        마음 맞는 팀원을 찾아보세요!
      </h1>
      <div className="view-options">
        <div className="filter-options">
          <SwitchToggle
            left="전체 보기"
            right="모집 중"
            setChecked={viewAllStatus}
            width="100px"
            onClick={() => {
              setViewStatus(!viewAllStatus);
            }}
          />
          <SkillFilterSelector
            isOpened={filterModalDisplay}
            setSkillFilter={setSkillFilter}
            setModalDisplay={() => setFilterModalDisplay(!filterModalDisplay)}
          />
        </div>
        <div className="sort-options">
          {Object.keys(sortOptions).map((option) => (
            <button
              key={option}
              className={option === sortOption ? 'active-option' : ''}
              onClick={() => setSortOption(option)}
            >
              {option}
            </button>
          ))}
        </div>
      </div>
      <ArticlesGrid>
        {articlesList.map((article) => {
          // console.log(article.articleId, article.title);
          return (
            <ArticleCard
              key={article.articleId}
              articleId={article.articleId}
              isCompleted={article.isCompleted}
              title={article.title}
              startDay={article.startDay}
              endDay={article.endDay}
              frontend={article.frontend}
              backend={article.backend}
              hashtags={article.hashtags}
              skills={article.skills}
              memberName={article.memberName}
              heartCount={article.heartCount}
              views={article.views}
            />
          );
        })}
      </ArticlesGrid>
      <ScrollTopButton />
      {isFetching && <Loading>게시글 불러오는 중...</Loading>}
    </Container>
  );
};

export default Main;
